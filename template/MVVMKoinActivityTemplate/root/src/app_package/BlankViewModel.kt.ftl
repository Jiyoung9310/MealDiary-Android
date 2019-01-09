package ${packageName}.${packName}

import ${applicationPackage?replace('.debug|.qa|.release', '', 'r')}.base.BaseViewModel

internal interface ${Name}ViewModel {
    interface Inputs {

    }

    interface Outputs {

    }

    class ViewModel() : BaseViewModel(), Inputs, Outputs {

        val inputs: Inputs = this
        val outputs: Outputs = this

        init {

        }

    }
}